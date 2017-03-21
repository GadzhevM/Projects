package businesstree.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,
				reason = "Bad request")
public class BadRequest extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/* **********************************************************
	 * if rest service throw this exception, Spring automatically
	 * will maps it to http status BAD_REQUEST
	 * **********************************************************/
}
