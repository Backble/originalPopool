package kr.co.memberservice.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MemberMstDto {

    @Builder
    @Getter
    @AllArgsConstructor
    public static class CREATE{

        @ApiModelProperty(example = "사용할 아이디")
        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min = 5, max = 16, message = "ID는 5 ~ 16자를 입력해주세요")
        private String identity;
        @ApiModelProperty(example = "사용할 비밀번호")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String password;
        @ApiModelProperty(example = "사용할 비밀번호 확인")
        @NotBlank(message = "확인 비밀번호를 입력해주세요.")
        private String checkPassword;
        @ApiModelProperty(example = "홍길동")
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;
        @ApiModelProperty(example = "YYYYmmDD")
        @NotBlank(message = "생년월일을 입력해주세요.")
        private String birth;
        @ApiModelProperty(example = "010-XXXX-XXXX")
        @NotBlank(message = "휴대폰 번호를 입력해주세요.")
        private String phone;
        @ApiModelProperty(example = "MALE or FEMALE")
        @NotBlank(message = "성별을 입력해주세요.")
        private String gender;
        @ApiModelProperty(example = "ROLE_MEMBER")
        @NotBlank(message = "권한을 입력해주세요.")
        private String memberRole;
        @ApiModelProperty(example = "RANK_NORMAL or RANK_CORPORATE")
        @NotBlank(message = "회원 종류를 입력해주세요.")
        private String memberRank;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TOKEN{

    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class LOGIN{

        @ApiModelProperty(example = "사용자 아이디")
        @NotBlank(message = "아이디를 입력해주세요")
        private String identity;

        @ApiModelProperty(example = "사용자 비밀번호")
        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class UPDATE{

    }
}
