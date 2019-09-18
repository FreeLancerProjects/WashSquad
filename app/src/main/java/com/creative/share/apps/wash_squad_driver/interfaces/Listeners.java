package com.creative.share.apps.wash_squad_driver.interfaces;

import com.creative.share.apps.wash_squad_driver.models.ContactUsModel;

public interface Listeners {

    interface LoginListener {
        void checkDataLogin(String phone_code, String phone, String password);
    }

    interface SkipListener
    {
        void skip();
    }
    interface CreateAccountListener
    {
        void createNewAccount();
    }

    interface ShowCountryDialogListener
    {
        void showDialog();
    }

    interface SignUpListener
    {
        void checkDataSignUp(String name, String phone_code, String phone, String password);

    }

    interface EditProfileListener
    {
        void checkDataEditProfile(String name);

    }
    interface RatingListener
    {
        void checkDataRating(String desc);

    }
    interface BackListener
    {
        void back();
    }



    interface ContactListener
    {
        void sendContact(ContactUsModel contactUsModel);
    }


}
