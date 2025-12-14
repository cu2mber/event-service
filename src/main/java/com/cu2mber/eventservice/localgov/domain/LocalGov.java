package com.cu2mber.eventservice.localgov.domain;

import com.cu2mber.eventservice.event.domain.Event;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// 추후 event.client 구조에서 member-service 호출용 FeignClient로 불러와야 합니다. 지금은 test를 위해 임시 도메인으로 뒀습니다.
@Getter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "local_govs", catalog = "localgov")
public class LocalGov {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short localNo;

    @NonNull
    @Column(length = 50, nullable = false)
    private String localDistrict;

    @Column(length = 50)
    private String localName;


    @OneToMany(mappedBy = "localGov", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Event> events = new ArrayList<>();

    public LocalGov(@NonNull String localDistrict, String localName){
        this.localDistrict = localDistrict;
        this.localName = localName;
    }


}
