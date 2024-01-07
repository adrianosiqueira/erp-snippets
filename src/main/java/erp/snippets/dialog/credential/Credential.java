package erp.snippets.dialog.credential;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Credential {
    private String username;
    private String password;
}
