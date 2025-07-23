public class Results<T> {
	
	public static <T> Result<T>  success(){
		Result<T> result = new Result<>();
		
		result.setSuccess(true);
		result.setMessage("成功");

		return result;
	}
	
	public static <T> Result<T> success(T data){
		Result<T> result = new Result<>();
		
		result.setSuccess(true);
		result.setData(data);
		result.setMessage("成功");

		return result;
	}
	
	
	public static <T> Result<T> warn(String msg){
		Result<T> result = new Result<>();
		
		result.setSuccess(false);
		result.setMessage(msg);

		return result;
	}

	public static <T> Result<T> error(String msg){
		Result<T> result = new Result<>();

		result.setSuccess(false);
		result.setMessage(msg);

		return result;
	}
}