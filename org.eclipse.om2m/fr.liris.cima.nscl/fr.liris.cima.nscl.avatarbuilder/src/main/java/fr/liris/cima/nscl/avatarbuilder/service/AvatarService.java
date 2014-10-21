package fr.liris.cima.nscl.avatarbuilder.service;

import java.util.List;
import java.util.Set;

import fr.liris.cima.nscl.avatarbuilder.Avatar;
import fr.liris.cima.nscl.avatarbuilder.Capability;

public interface AvatarService {

	public Avatar createAvatar(String id, String protocol, String uri);
	public List<Capability> getCapabilities(String deviceId);
}