package com.temp.common.mq.commission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Accessors(chain = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageBasic implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private MessageType type;
	private Date time;
	private String body;
	
}
