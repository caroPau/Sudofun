package hsos.prog3.sudofun.model;
import hsos.prog3.sudofun.viewmodel.DataViewModel;

/**
 * Klasse repräsentiert die notwendigen Informationen für den Login.
 */
public class Login {

    private UserEntity userEntity;

    /**
     *  Getter
     */

    public UserEntity getUserEntity(){
        return userEntity;
    }

    /**
     *  Setter
     */
    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}
