package kr.co.memberservice.domain.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass   // 객체 입장에서 공통 매핑 정보가 필요할 때 사용
@EntityListeners(AuditingEntityListener.class) // 공통적으로 가지고 있는 필드나 컬럼들을 시간에 대해서 자동으로 값을 넣어주는 기능
@EqualsAndHashCode(of="id", callSuper = false)
@Getter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "del_yn", nullable = false, length = 1)
    protected String del_yn = "N";

    @CreatedDate            //최초 생성 시간
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Timestamp created_at = null;

    @LastModifiedDate       //마지막 수정 시간
    @Column(name = "updated_at", nullable = false)
    protected Timestamp updated_at = null;

    @Column(name = "use_member")
    protected Long use_Member;

    public void deleted() {
        this.del_yn = "Y";
    }

    public void reCreated(){
        this.del_yn = "N";
    }
}
