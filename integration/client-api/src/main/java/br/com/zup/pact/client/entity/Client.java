package br.com.zup.pact.client.entity;

import br.com.zup.pact.client.dto.ClientDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Client {

    private Integer id;
    private Integer accountId;
    private String name;
    private String finalName;
    private Integer age;

    public static ClientDetailsDTO fromEntityToDto(Client client) {
        if (Objects.nonNull(client)){
            return ClientDetailsDTO.builder()
                    .id(client.getId())
                    .accountId(client.getAccountId())
                    .age(client.getAge())
                    .name(client.getName())
                    .finalName(client.getFinalName())
                    .build();
        }
        return null;
    }
}
