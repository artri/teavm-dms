package org.fastgrow.teadms.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.teavm.flavour.json.JsonPersistable;

@JsonPersistable
public class OrderEditDto {
	public OrderStatusDto status;
	public String receiverName;
	public String address;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
	public Date date;

	public List<OrderEditItemDto> items = new ArrayList<>();
}
