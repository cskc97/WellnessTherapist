package apps.everythingforward.com.wellnesstherapist.models;

import fr.xebia.android.freezer.annotations.Id;
import fr.xebia.android.freezer.annotations.Model;

/**
 * Created by santh on 5/20/2017.
 */

@Model
public class TherapistProfileDB {
    @Id String email;
    String imagePath;

    public TherapistProfileDB() {
    }

    public TherapistProfileDB(String email, String imagePath) {
        this.email = email;
        this.imagePath = imagePath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
