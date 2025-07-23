import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;

    public BizException(String message) {
        this.code = 500;
        this.message = message;
    }
}
