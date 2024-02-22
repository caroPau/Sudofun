package hsos.prog3.sudofun.model;
import hsos.prog3.sudofun.viewmodel.DataViewModel;

public class Login {

    private String username;
    private DataViewModel dataViewModel;

    /**
     *  Getter
     */
    public String getUsername() {
        return username;
    }
    public DataViewModel getDataViewModel() {
        return dataViewModel;
    }

    /**
     *  Setter
     */
    public void setUsername(String username) {
        this.username = username;
    }
    public void setDataViewModel(DataViewModel dataViewModel) {
        this.dataViewModel = dataViewModel;
    }

}
