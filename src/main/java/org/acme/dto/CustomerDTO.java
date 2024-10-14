package org.acme.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerDTO {

    @NotNull
    @Size(min = 9, max = 9)
    @Column(nullable = false, unique = true)
    private String vat;

    @NotNull
    @Size(max = 30)
    @Column(name = "first_name", length = 30)
    private String firstName;

    @NotNull
    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotNull
    @Email
    @Size(max = 80)
    @Column(unique = true, name = "email", length = 80)
    private String email;

    @Size(max = 20)
    @Column(name = "mobile_phone", length = 20)
    private String mobilePhone;

}
