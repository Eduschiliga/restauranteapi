package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.mapper;

import br.com.fiap.restauranteapi.application.domain.address.AddressId;
import br.com.fiap.restauranteapi.application.domain.user.UserId;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.GetUserByTokenOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.user.CreateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.user.CreateUserOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.get.GetUserByIdOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ListUserOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ListUsersByNameOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.password.UpdatePasswordInput;
import br.com.fiap.restauranteapi.application.ports.inbound.password.UpdatePasswordOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.update.user.UpdateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.update.user.UpdateUserOutput;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.UserDTO;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.create.CreateUserDTO;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.update.UpdatePasswordDTO;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.update.UpdateUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
    UpdateUserInput fromUpdateDTO(UpdateUserDTO dto);

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

    @Mapping(target = "userId", source = "user.userId.value")
    UpdatePasswordInput fromUpdatePasswordDTO(UpdatePasswordDTO updatePasswordDto, GetUserByTokenOutput user);
}
