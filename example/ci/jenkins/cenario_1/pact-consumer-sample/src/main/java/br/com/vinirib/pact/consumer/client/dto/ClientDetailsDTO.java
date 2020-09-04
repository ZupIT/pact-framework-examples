package br.com.vinirib.pact.consumer.client.dto;

import br.com.vinirib.pact.consumer.client.entity.Client;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDetailsDTO {

    private Integer id;
    private Integer accountId;
    private String name;
    private String finalName;
    private Integer age;

    public static Client fromDtoToEntity(ClientDetailsDTO clientDetailsDTO) {
        Objects.requireNonNull(clientDetailsDTO, "Client must not null to build Client");
        return Client.builder()
                .id(clientDetailsDTO.getId())
                .accountId(clientDetailsDTO.getAccountId())
                .name(clientDetailsDTO.getName())
                .finalName(clientDetailsDTO.getFinalName())
                .age(clientDetailsDTO.getAge())
                .build();

    }
}
