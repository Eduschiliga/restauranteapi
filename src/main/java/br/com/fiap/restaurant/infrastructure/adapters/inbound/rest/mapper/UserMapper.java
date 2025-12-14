package br.com.fiap.restaurant.infrastructure.adapters.inbound.rest.mapper;

import br.com.fiap.restaurant.application.domain.address.AddressId;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.ports.inbound.create.user.CreateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.create.user.CreateUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.get.GetUserByIdOutput;
import br.com.fiap.restaurant.application.ports.inbound.list.ListUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.list.ListUsersByNameOutput;
import br.com.fiap.restaurant.application.ports.inbound.password.UpdatePasswordInput;
import br.com.fiap.restaurant.application.ports.inbound.password.UpdatePasswordOutput;
import br.com.fiap.restaurant.application.ports.inbound.update.user.UpdateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.update.user.UpdateUserOutput;
import br.com.fiap.restaurant.model.CreateUserDTO;
import br.com.fiap.restaurant.model.UpdatePasswordDTO;
import br.com.fiap.restaurant.model.UpdateUserDTO;
import br.com.fiap.restaurant.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(CreateUserOutput output);
    UserDTO toDTO(UpdatePasswordOutput output);
    UserDTO toDTO(UpdateUserOutput output);
    UserDTO toDTO(GetUserByIdOutput output);
    List<UserDTO> toDTO(List<ListUserOutput> output);
    List<UserDTO> toListDTO(List<ListUsersByNameOutput> output);

    CreateUserInput fromDTO(CreateUserDTO dto);

    @Mapping(target = "userId", source = "userId")
    UpdateUserInput fromUpdateDTO(UpdateUserDTO dto, String userId);

    default String map(UserId value) {
        return value != null ? value.toString() : null;
    }

    default UserId mapUserId(String value) {
        return value != null ? UserId.from(value) : null;
    }

    default String map(AddressId value) {
        return value != null ? value.toString() : null;
    }

    default AddressId mapAddressId(String value) {
        return value != null ? AddressId.from(value) : null;
    }

    @Mapping(target = "userId", source = "userId")
    UpdatePasswordInput fromUpdatePasswordDTO(UpdatePasswordDTO updatePasswordDto, String userId);

    default OffsetDateTime map(LocalDateTime value) {
        return value != null ? value.atOffset(ZoneOffset.of("-03:00")) : null;
    }
}
