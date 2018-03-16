package com.nativapps.arpia.model.dto;

import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class EstablishmentRequest extends EstablishmentData{
    
    private List<AddressRequest> addresses;
    
    private List<AdministratorRequest> administrators;

    public EstablishmentRequest() {
    }

    public EstablishmentRequest(String nit, String name, String observations) {
        super(nit, name, observations);
    }

    public List<AddressRequest> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressRequest> addresses) {
        this.addresses = addresses;
    }

    public List<AdministratorRequest> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<AdministratorRequest> administrators) {
        this.administrators = administrators;
    }
}
