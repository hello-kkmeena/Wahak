package com.wahak.pojo;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.util.List;

/**
 * @author krishna.meena
 */
@Getter
@Setter
@Builder
public class EmailSenderPojo {

    private String subject;

    private String content;

    private List<String> to;

    private List<String> cc;

    private List<String> bcc;

    private boolean isHtml;

}
