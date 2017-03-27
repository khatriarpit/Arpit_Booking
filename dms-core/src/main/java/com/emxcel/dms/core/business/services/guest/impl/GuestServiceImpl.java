package com.emxcel.dms.core.business.services.guest.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import com.emxcel.dms.core.business.repositories.guest.GuestRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.guest.GuestService;
import com.emxcel.dms.core.model.guest.Guest;

@Service("guestService")
public class GuestServiceImpl extends DMSEntityServiceImpl<Long, Guest> implements GuestService {

    private GuestRepository guestRepository;

    @Inject
    public GuestServiceImpl(GuestRepository guestRepository) {
        super(guestRepository);
        this.guestRepository = guestRepository;
    }

    @Override
    public Guest getGuestDetailByContactNo(String contactNo) {
        return guestRepository.getGuestDetailByContactNo(contactNo);
    }

    @Override
    public void updateTokenFCMWithContactNo(String contactNo, String tokenID) {
        guestRepository.updateTokenFCMWithContactNo(contactNo, tokenID);
    }

    /* (non-Javadoc)
     * @see com.emxcel.dms.core.business.services.client.ClientService#changePasswordByEmail(java.lang.String, java.lang.String)
     */
    @Override
    public void changePasswordByEmail(String email, String password) {
        guestRepository.changePasswordByEmail(email, password);
    }

    @Override
    public Guest loginCheck(String email, String password) {
        return guestRepository.loginCheck(email, password);
    }

    @Override
    public Guest checkEmail(String email) {
        return guestRepository.checkEmail(email);
    }

    /* (non-Javadoc)
     * @see com.emxcel.dms.core.business.services.client.GuestService#getGuestDetailByEmailID(java.lang.String)
     */
    @Override
    public Guest getGuestDetailByEmailID(String emailId) {
        return guestRepository.getGuestDetailByEmailID(emailId);
    }

    /* (non-Javadoc)
     * @see com.emxcel.dms.core.business.services.client.GuestService#getLastID()
     */
    @Override
    public Long getLastID() {
        return guestRepository.getLastID();
    }

    @Override
    public void updateGuestDetailByEmail(String emailId, String personName, String password, String tokenID,
                                         String mname, String lname, String fname) {
        guestRepository.updateGuestDetailByEmail(emailId, personName, password, tokenID, mname, lname, fname);

    }

	@Override
	public Long saveGuest(Guest guest) {
		guest = guestRepository.save(guest);
		return guest.getId();
	}

    @Override
    public Guest checkemailorcontact(String emailIDOrContactNo) {
        return guestRepository.checkemailorcontact(emailIDOrContactNo);
    }

	@Override
	public List<Guest> getContactNumberList(String query) {
		return guestRepository.getContactNumberList(query);
	}


}
