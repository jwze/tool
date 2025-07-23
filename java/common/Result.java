import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "返回结果")
public class Result<T> {
    @Schema(description = "返回数据")
    private T data;
    @Schema(description = "是否成功")
    private boolean success;
    @Schema(description = "提示")
    private String message;
}
