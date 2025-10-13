package com.cu2mber.eventservice.localgov.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 추후 event.client 구조에서 member-service 호출용 FeignClient로 불러와야 합니다. 지금은 test를 위해 임시 도메인으로 뒀습니다.
@Getter
@Entity
@NoArgsConstructor
@Table(name = "local_govs")
public class LocalGov {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long localNo;

    private String localDistrict;

    private String localName;

    private String localContact;

    private String localEmail;
}
