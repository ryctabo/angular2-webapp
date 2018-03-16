package com.nativapps.arpia.service;

import com.nativapps.arpia.database.dao.EntityControllerFactory;
import com.nativapps.arpia.model.SystemConfigManager;

/**
 * The <strong>ServiceFactory</strong> class provided methods to get services
 * implementations.
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0-SNAPSHOT
 */
public class ServiceFactory {

    public static ParticularService getParticularService() {
        return new ParticularServiceImpl();
    }

    public static AddressService getAddressService() {
        return new AddressServiceImpl();
    }

    public static EmailService getEmailService() {
        return new EmailServiceImpl();
    }

    public static PhoneService getPhoneService() {
        return new PhoneServiceImpl();
    }

    public static EstablishmentService getEstablishmentService() {
        return new EstablishmentServiceImpl();
    }

    public static AdministratorService getAdministratorService() {
        return new AdministratorServiceImpl();
    }

    public static RoleService getRoleService() {
        return new RoleServiceImpl();
    }

    public static UserService getUserService() {
        return new UserServiceImpl(EntityControllerFactory.getUserController());
    }

    public static MessengerService getMessengerService() {
        return new MessengerServiceImpl(EntityControllerFactory.getMessengerController());
    }

    public static InventoryService getInventoryService() {
        return new InventoryServiceImpl(EntityControllerFactory
                .getInventoryController());
    }

    public static FaultService getFaultService() {
        return new FaultServiceImpl();
    }

    public static ReferenceService getReferenceService() {
        return new ReferenceServiceImpl(
                EntityControllerFactory.getReferenceController(),
                EntityControllerFactory.getCurriculumVitaeController()
        );
    }

    public static OperationService getOperationService() {
        return new OperationServiceImpl(EntityControllerFactory
                .getOperationController());
    }

    public static VehicleService getVehicleService() {
        return new VehicleServiceImpl(EntityControllerFactory
                .getVehicleController());
    }

    public static ParticularParamService getParticularParameterService() {
        return new ParticularParamServiceImpl();
    }

    public static EstablishmentParamService getEstablishmentParamService() {
        return new EstablishmentParamServiceImpl();
    }

    public static MessengerFBService getMessengerBlackListService() {
        return new MessengerFBServiceImpl();
    }

    public static BonusService getBonusService() {
        return new BonusServiceImpl();
    }

    public static ReliabilityService getReliabilityService() {
        return new ReliabilityServiceImpl();
    }

    public static NeighborhoodService getNeighborhoodService() {
        return new NeighborhoodServiceImpl(
                EntityControllerFactory.getNeighborhoodController(),
                EntityControllerFactory.getCustomerDataController(),
                EntityControllerFactory.getDomicileExecuteController()
        );
    }

    public static CustomerDataService getCustomerDataService() {
        return new CustomerDataServiceImpl(EntityControllerFactory
                .getCustomerDataController());
    }

    public static CreditInfoService getCreditInfoService() {
        return new CreditInforServiceImpl();
    }

    public static MessengerActionService getMessengerActionService() {
        return new MessengerActionServiceImpl();
    }

    public static CustomerBanService getCustomerBanService() {
        return new CustomerBanServiceImpl();
    }

    public static DismissalHistoryService getDismissalHistoryService() {
        return new DismissalHistoryServiceImpl(EntityControllerFactory
                .getDismissalHistoryController());
    }

    public static DocumentService getDocumentService() {
        return new DocumentServiceImpl(EntityControllerFactory
                .getDocumentController());
    }

    public static MobileNumberService getMobileNumberService() {
        return new MobileNumberServiceImpl(EntityControllerFactory
                .getNumberMobileController());
    }

    public static QCRService getQCRService() {
        return new QCRServiceImpl(
                EntityControllerFactory.getQCRController(),
                EntityControllerFactory.getCustomerDataController(),
                EntityControllerFactory.getMessengerController()
        );
    }

    public static ProhibitionService getProhibitionService() {
        return new ProhibitionServiceImpl();
    }

    public static RestrictionService getRestrictionService() {
        return new RestrictionServiceImpl();
    }

    public static StockService getStockService() {
        return new StockServiceImpl(
                EntityControllerFactory.getStockController(),
                EntityControllerFactory.getInventoryController()
        );
    }

    public static ShortcutService getShortcutService() {
        return new ShortcutServiceImpl(EntityControllerFactory.getShortcutController());
    }

    public static BaseService getBaseService() {
        return new BaseServiceImpl(
                EntityControllerFactory.getBaseController(),
                EntityControllerFactory.getMessengerController(),
                EntityControllerFactory.getOperationController(),
                EntityControllerFactory.getCashController(),
                SystemConfigManager.get()
        );
    }

    public static CurriculumVitaeService getCurriculumVitaeService() {
        return new CurriculumVitaeServiceImpl(EntityControllerFactory
                .getCurriculumVitaeController());
    }

    public static PersonService getPersonService() {
        return new PersonServiceImpl(EntityControllerFactory
                .getPersonController());
    }

    public static AbbreviationService getabAbbreviationService() {
        return new AbbreviationServiceImpl(EntityControllerFactory
                .getAbbreviationController());
    }

    public static SpecialDayService getSpecialDayService() {
        return new SpecialDayServiceImpl();
    }

    public static SystemConfigService getSystemConfigService() {
        return new SystemConfigServiceImpl();
    }

    public static EvaluationService getMessengerEvalService() {
        return new EvaluationServiceImpl(
                EntityControllerFactory.getEvaluationController(),
                EntityControllerFactory.getMessengerController()
        );
    }

    public static IssueService getIssueService() {
        return new IssueServiceImpl(EntityControllerFactory.getIssueController());
    }

    public static PenaltyService getPenaltyService() {
        return new PenaltyServiceImpl();
    }

    public static MarketingObsService getMarketingObsService() {
        return new MarketingObsServiceImpl(
                EntityControllerFactory.getMarketingObsController(),
                EntityControllerFactory.getCustomerDataController()
        );
    }

    public static CashTallyingService getCashTallyingService() {
        return new CashTallyingServiceImpl(
                EntityControllerFactory.getCashTallyingController(),
                SystemConfigManager.get());
    }

    public static CashService getCashService() {
        return new CashServiceImpl(
                EntityControllerFactory.getCashController(),
                EntityControllerFactory.getOperationController()
        );
    }

    public static DailyOperationService getDailyOperationService() {
        return new DailyOperationServiceImpl(
                EntityControllerFactory.getDailyOperationController(),
                EntityControllerFactory.getCashTallyingController(),
                EntityControllerFactory.getOperationController()
        );
    }

    public static DomicileService getDomicileService() {
        return new DomicileServiceImpl(EntityControllerFactory
                .getDomicileController());
    }

    public static SecurityDepositService getSecurityDepositService() {
        return new SecurityDepositServiceImpl();
    }

    public static SecurityDepositPaymentService getSecurityDepositPaymentService() {
        return new SecurityDepositPaymentServiceImpl();
    }

    public static DomicileReviewService getDomicileReviewService() {
        return new DomicileReviewServiceImpl(
                EntityControllerFactory.getDomicileReviewController(),
                EntityControllerFactory.getDomicileExecuteController()
        );
    }

    public static MarketingMessageService getMarketingMessageService() {
        return new MarketingMessageServiceImpl(
                EntityControllerFactory.getMarketingMessageController(),
                EntityControllerFactory.getCustomerDataController()
        );
    }

    public static DomicileExecuteService getDomicileExecuteService() {
        return new DomicileExecuteServiceImpl(
                EntityControllerFactory.getDomicileExecuteController(),
                EntityControllerFactory.getParticularController()
        );
    }

    public static GoodStandingService getGoodStandingService() {
        return new GoodStandingServiceImpl(
                EntityControllerFactory.getGoodStandingController(),
                EntityControllerFactory.getOperationController(),
                EntityControllerFactory.getMessengerController()
        );
    }

    public static FestiveDayService getFestiveDayService() {
        return new FestiveDayServiceImpl(EntityControllerFactory
                .getFestiveDayController());
    }

    public static SpecialDateService getSpecialDateService() {
        return new SpecialDateServiceImpl(EntityControllerFactory
                .getSpecialDateController());
    }

    public static MonitoringService getMonitoringService() {
        return new MonitoringServiceImpl(EntityControllerFactory
                .getMonitoringController());
    }

    public static DiscountService getDiscountService() {
        return new DiscountServiceImpl(EntityControllerFactory
                .getDiscountController());
    }

    public static DiscountChangeLogService getDiscountChangeLogService() {
        return new DiscountChangeLogServiceImpl(EntityControllerFactory
                .getDiscountChangeLogController());
    }

    public static AssignmentService getAssignmentService() {
        return new AssignmentServiceImpl(EntityControllerFactory
                .getAssignmentController());
    }

    public static ShiftplanningService getShiftplanningService() {
        return new ShiftplanningServiceImpl(EntityControllerFactory
                .getShiftplanningController());
    }

    public static ShiftService getShiftService() {
        return new ShiftServiceImpl(EntityControllerFactory.getShiftController(),
                EntityControllerFactory.getShiftplanningController(),
                EntityControllerFactory.getMessengerController(),
                EntityControllerFactory.getShiftAssignmentController()
        );
    }

    public static ShiftAssignmentService getShiftAssignmentService() {
        return new ShiftAssignmentServiceImpl(
                EntityControllerFactory.getShiftAssignmentController(),
                EntityControllerFactory.getShiftController(),
                EntityControllerFactory.getMessengerController()
        );
    }

    public static ShiftCheckService getCheckService() {
        return new ShiftCheckServiceImpl(
                EntityControllerFactory.getShiftCheckController(),
                EntityControllerFactory.getShiftAssignmentController(),
                EntityControllerFactory.getShiftCheckKeyController()
        );
    }

    public static ShiftCheckKeyService getShiftCheckKeyService() {
        return new ShiftCheckKeyServiceImpl(
                EntityControllerFactory.getShiftCheckKeyController(),
                EntityControllerFactory.getShiftCheckController()
        );
    }

    public static MapPointService getMapPointService() {
        return new MapPointServiceImpl(
                EntityControllerFactory.getMapPointController(),
                EntityControllerFactory.getScheduleController(),
                EntityControllerFactory.getPricingController()
        );
    }

    public static EgressService getEgressService() {
        return new EgressServiceImpl(
                EntityControllerFactory.getEgressController(),
                EntityControllerFactory.getCashController()
        );
    }

    public static DebtService getDebtService() {
        return new DebtServiceImpl(EntityControllerFactory.getDebtController());
    }

    public static PaymentService getPaymentService() {
        return new PaymentServiceImpl(
                EntityControllerFactory.getPaymentController(),
                EntityControllerFactory.getDebtController(),
                EntityControllerFactory.getCashController()
        );
    }

    public static MSettlementService getMSettlementService() {
        return new MSettlementServiceImpl(
                EntityControllerFactory.getMSettlementController(),
                EntityControllerFactory.getDomicileExecuteController(),
                EntityControllerFactory.getShiftCheckController()
        );
    }

    public static FileService getFileService() {
        return new FileServiceImpl();
    }

    public static BirthdayService getBirthdayService() {
        return new BirthdayServiceImpl(EntityControllerFactory.getBirthdayController());
    }

    public static ChipService getChipService() {
        return new ChipServiceImpl(
                EntityControllerFactory.getChipController(),
                EntityControllerFactory.getMessengerController(),
                EntityControllerFactory.getInventoryController()
        );
    }

    public static WorkShiftPointService getWorkShiftPointService() {
        return new WorkShiftPointServiceImpl(
                EntityControllerFactory.getWorkShiftPointController(),
                EntityControllerFactory.getMessengerController(),
                EntityControllerFactory.getMapPointController()
        );
    }

    public static GoodStandingRService getGoodStandingRService() {
        return new GoodStandingRServiceImpl(EntityControllerFactory.getGoodStandingController());
    }
}
