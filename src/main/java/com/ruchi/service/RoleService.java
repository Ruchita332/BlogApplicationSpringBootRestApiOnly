package com.ruchi.service;

import java.util.Set;

import com.ruchi.payloads.RoleDto;

public interface RoleService {
	public RoleDto getRole(long rId);

	public String updateRole(RoleDto roleDto, long roleId, long userId);

	public String createRole(RoleDto roleDto, long userId);

	public String deleteRole(long roleId, long userId);

	public Set<RoleDto> viewUserRole(long userId);
}
