package ec.gob.mag.central.catalogo.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
	private HttpStatus status;
	private Date timestamp;
	private String message;
	private String details;
	private String detailsJson;

	public ExceptionResponse(HttpStatus status, Date timestamp, String message, String details, String detailsJson) {
		this.status = status;
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
		this.detailsJson = detailsJson;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getDetailsJson() {
		return detailsJson;
	}

	public void setDetailsJson(String detailsJson) {
		this.detailsJson = detailsJson;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
