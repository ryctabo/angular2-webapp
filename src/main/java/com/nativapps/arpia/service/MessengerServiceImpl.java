package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.*;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.model.dto.*;
import com.nativapps.arpia.service.converter.DtoConverter;
import com.nativapps.arpia.service.converter.InventorySimpleConverter;
import com.nativapps.arpia.service.exception.ServiceException;
import com.nativapps.arpia.service.validator.MessengerValidator;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.2.2
 */
public class MessengerServiceImpl extends GenericService implements MessengerService,
        DtoConverter<Messenger, MessengerRequest, MessengerResponse> {

    private final MessengerDao controller;

    public MessengerServiceImpl(MessengerDao controller) {
        this.controller = controller;
    }

    /**
     * Validate if messenger have all attributes for save to database.
     *
     * @param messenger messenger to validate
     * @throws BadRequestException if the messenger data is null
     * @throws ServiceException    if the messenger data don't have any attribute
     *                             required
     */
    private void validateMessenger(MessengerRequest messenger)
            throws BadRequestException, ServiceException {
        if (messenger == null) {
            throw new BadRequestException(config.getString("messenger.is_null"));
        } else {
            ErrorMessageData emd = new ErrorMessageData();
            MessengerValidator.evaluateMessenger(messenger, emd, config);

            //verify if exists errors
            if (!emd.getMessages().isEmpty()) {
                emd.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
                throw new ServiceException(emd);
            }
        }
    }

    /**
     * Get messenger entity by messenger id provided.
     *
     * @param id messenger ID
     * @return a messenger data
     * @throws BadRequestException if the messenger ID is null or less than or
     *                             equal to 0.
     * @throws NotFoundException   if the messenger obtained is null.
     */
    private Messenger getEntity(Long id) throws BadRequestException,
            NotFoundException {
        if (id == null || id <= 0) {
            throw new BadRequestException(config.getString("messenger.id_required"));
        }

        Messenger messenger = controller.find(id);
        if (messenger == null) {
            String msg = String.format(config.getString("messenger.not_found"), id);
            throw new NotFoundException(msg);
        }
        return messenger;
    }

    @Override
    public MessengerResponse add(MessengerRequest messenger) {
        validateMessenger(messenger);

        //get operation
        OperationDao oController = EntityControllerFactory.getOperationController();
        Operation operation = oController.find(messenger.getOperationId());

        //create a messenger user
        String displayName = String.format("%s %s", messenger.getName(),
                messenger.getLastName());
        String mainEmail = messenger.getEmails().get(0).getAddress();

        UserDao uController = EntityControllerFactory.getUserController();
        if (uController.findByEmail(mainEmail) != null) {
            final String FORMAT = config.getString("messenger.user_email");
            throw new BadRequestException(String.format(FORMAT, mainEmail));
        }
        User user = new User(mainEmail, messenger.getPassword(), displayName,
                UserType.MESSENGER);
//        tempUser.setUsername(String.format("%s-%d", operation.getAlias(), 1));
        user.getOperations().add(operation);

        //assign user to messenger
        Messenger eMessenger = convertToEntity(messenger);
        eMessenger.setUser(user);

        //save messenger
        return convertToDto(controller.save(eMessenger));
    }

    @Override
    public MessengerResponse get(Long id) {
        return convertToDto(getEntity(id));
    }

    @Override
    public ListResponse<MessengerResponse> getAll(Boolean dismissal, Integer category,
                                                  String search, int start, int size, String orderBy, OrderType orderType,
                                                  Boolean retire) {
        if (start < 0)
            throw new BadRequestException(config.getString("pagination.start"));
        if (size <= 0)
            throw new BadRequestException(config.getString("pagination.size"));

        List<MessengerResponse> messengers = new ArrayList<>();
        for (Messenger messenger : controller.findAll(dismissal, category, search,
                start, size, orderBy, orderType, retire)) {
            messengers.add(convertToDto(messenger));
        }
        long totalCount = controller.getCount(dismissal, category, search, retire);
        return new ListResponse<>(totalCount, messengers);
    }

    @Override
    public MessengerResponse update(Long id, MessengerRequest messenger) {
        validateMessenger(messenger);
        Messenger currentMessenger = getEntity(id);

        //change person data
        currentMessenger.setIdentification(messenger.getIdentification());
        currentMessenger.setName(messenger.getName());
        currentMessenger.setLastName(messenger.getLastName());
        currentMessenger.setBirthday(messenger.getBirthday());
        currentMessenger.setGender(messenger.getGender());

        //change messenger data
        //currentMessenger.setDismissal(messenger.getDismissal());
        currentMessenger.setObservations(messenger.getObservations());
        currentMessenger.setPhoto(messenger.getPhoto());
        currentMessenger.setCategory(currentMessenger.getCategory());

        CurriculumVitaeData cv = messenger.getCurriculumVitae();
        CurriculumVitae currentCv = currentMessenger.getCurriculumVitae();

        //change curriculum vitae
        currentCv.setAcademicLevel(cv.getAcademicLevel());
        currentCv.setBirthCity(cv.getBirthCity());
        currentCv.setDistrict(cv.getDistrict());
        currentCv.setMilitaryCard(cv.getMilitaryCard());
        currentCv.setCivilStatus(cv.getCivilStatus());

        //change home data
        HomeData home = cv.getHome();
        currentCv.getHome().setHomeownership(home.getHomeownership());
        currentCv.getHome().setMonthlyExpenses(home.getMonthlyExpenses());
        currentCv.getHome().setSonsNumber(home.getSonsNumber());
        currentCv.getHome().setStratum(home.getStratum());

        //change social security data
        SocialSecurityData ss = cv.getSocialSecurity();
        currentCv.getSocialSecurity().setNameARL(ss.getNameARL());
        currentCv.getSocialSecurity().setNameEPS(ss.getNameEPS());
        currentCv.getSocialSecurity().setPensionEntity(ss.getPensionEntity());

        return convertToDto(controller.save(currentMessenger));
    }

    @Override
    public MessengerResponse delete(Long id) {
        MessengerResponse data = get(id);
        controller.delete(id);
        return data;
    }
    
    @Override
    public MessengerResponse retire(Long id) {
        Messenger messenger = getEntity(id);
        if (messenger.isRetire())
            throw new BadRequestException(config.getString("messenger.is_retired"));
        else
            messenger.setRetire(true);
        
        messenger.setMobile(null);
        messenger.getUser().setUsername(null);
        messenger.getUser().setEnabled(false);
        
        return convertToDto(controller.save(messenger));
    }
    
    @Override
    public MessengerResponse comeback(Long id) {
        Messenger messenger = getEntity(id);
        if (!messenger.isRetire())
            throw new BadRequestException(config.getString("messenger.no_retired"));
        else
            messenger.setRetire(false);
        
        messenger.getUser().setEnabled(true);
        
        return convertToDto(controller.save(messenger));
    }

    @Override
    public List<InventoryInfoResponse> getInventoryInfo(Long messengerId) {
        getEntity(messengerId);
        List<InventoryInfo> inventory = controller.getInventoryInfo(messengerId);

        List<InventoryInfoResponse> response = new ArrayList<>();
        for (InventoryInfo iInfo : inventory) {
            InventoryInfoResponse iResponse = new InventoryInfoResponse();

            iResponse.setInventory(InventorySimpleConverter.instance()
                    .convertToDto(iInfo.getInventory()));
            iResponse.setLoans(iInfo.getLoans());
            iResponse.setReturns(iInfo.getReturns());

            response.add(iResponse);
        }
        return response;
    }

    @Override
    public Messenger convertToEntity(MessengerRequest d) {
        Messenger m = new Messenger();

        //convert person data
        m.setIdentification(d.getIdentification());
        m.setName(d.getName());
        m.setLastName(d.getLastName());
        m.setGender(d.getGender());
        m.setBirthday(d.getBirthday());

        //convert messenger data
        m.setDismissal(d.getDismissal() == null ? false : d.getDismissal());
        m.setObservations(d.getObservations());
        m.setPhoto(d.getPhoto());
        m.setCategory(d.getCategory() == null ? 0 : d.getCategory());

        //convert addresses if they exist
        NeighborhoodDao neiController = EntityControllerFactory
                .getNeighborhoodController();
        if (d.getAddresses() != null) {
            for (AddressRequest a : d.getAddresses()) {
                Neighborhood nei = neiController.find(a.getNeighborhood());
                m.addAddress(new Address(a.getTag(),
                        a.getResidentialAddress(), nei));
            }
        }

        //convert phones if they exist
        if (d.getPhones() != null) {
            for (PhoneRequest p : d.getPhones()) {
                m.addPhone(new Phone(p.getTag(), p.getNumber(), p.getPhoneType()));
            }
        }

        //convert emails if they exist
        if (d.getEmails() != null) {
            for (EmailRequest email : d.getEmails()) {
                m.addEmail(new Email(email.getAddress()));
            }
        }

        //convert curriculum vitae
        CurriculumVitaeRequest cvr = d.getCurriculumVitae();
        CurriculumVitae cv = new CurriculumVitae();
        cv.setCivilStatus(cvr.getCivilStatus());
        cv.setBirthCity(cvr.getBirthCity());
        cv.setAcademicLevel(cvr.getAcademicLevel());
        cv.setMilitaryCard(cvr.getMilitaryCard());
        cv.setDistrict(cvr.getDistrict());

        //references
        if (cvr.getReferences() != null && !cvr.getReferences().isEmpty()) {
            int index = 1;
            for (ReferenceRequest data : cvr.getReferences()) {
                Reference reference = new Reference();

                reference.getId().setIndex(index++);
                reference.setName(data.getName().trim());
                reference.setLastName(data.getLastName().trim());
                reference.setAddress(data.getAddress().trim());
                reference.setPhone(data.getPhoneNumber().trim());
                reference.setRelationship(data.getRelationship());
                reference.setType(data.getType());
                reference.setCurriculumVitae(cv);

                cv.addReference(reference);
            }
        }

        //convert social security
        SocialSecurityData ssd = cvr.getSocialSecurity();
        SocialSecurity ss = new SocialSecurity(ssd.getNameEPS(),
                ssd.getNameARL(), ssd.getPensionEntity());

        //convert home
        HomeData hd = cvr.getHome();
        Home home = new Home(hd.getStratum(), hd.getSonsNumber(),
                hd.getHomeownership(), hd.getMonthlyExpenses());

        cv.setSocialSecurity(ss);
        cv.setHome(home);
        m.setCurriculumVitae(cv);

        return m;
    }

    @Override
    public MessengerResponse convertToDto(Messenger m) {
        //convert list of addresses
        List<AddressResponse> aResponse = new ArrayList<>();
        if (m.getAddresses() != null) {
            for (Address a : m.getAddresses()) {
                Neighborhood nei = a.getNeighborhood();
                NeighborhoodResponse neiResponse
                        = new NeighborhoodResponse(nei.getId(), nei.getName());
                aResponse.add(new AddressResponse(
                        a.getTag(),
                        a.getResidentialAddress(),
                        neiResponse));
            }
        }

        //convert list of emails
        List<EmailResponse> eResponse = new ArrayList<>();
        if (m.getEmails() != null) {
            for (Email email : m.getEmails()) {
                eResponse.add(new EmailResponse(email.getAddress(),
                        email.isConfirmed()));
            }
        }

        //convert list of phones
        List<PhoneResponse> pResponse = new ArrayList<>();
        if (m.getPhones() != null) {
            for (Phone p : m.getPhones()) {
                pResponse.add(new PhoneResponse(p.getTag(), p.getNumber(),
                        p.getType(), p.isConfirmed()));
            }
        }

        Integer mobile = m.getMobile() != null ? m.getMobile().getId().getIndex() : null;

        //convert to messenger response
        long userId = m.getUserId() == 0 ? m.getUser().getId() : m.getUserId();
        long cvId = m.getCurriculumVitaeId() == 0 ? m.getCurriculumVitae().getId() : m.getCurriculumVitaeId();
        MessengerResponse response = new MessengerResponse(m.getId(), aResponse,
                eResponse, pResponse, cvId, userId, mobile, m.getIdentification(),
                m.getName(), m.getLastName(), m.getGender(), m.getBirthday(),
                m.isDismissal(), m.getObservations(), m.getPhoto(),
                m.getCategory());
        response.setRetire(m.isRetire());
        
        return response;
    }
}
