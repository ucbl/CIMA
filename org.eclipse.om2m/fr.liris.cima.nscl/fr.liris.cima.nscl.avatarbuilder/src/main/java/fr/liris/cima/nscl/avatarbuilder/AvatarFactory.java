package fr.liris.cima.nscl.avatarbuilder;

import java.util.List;

import fr.liris.cima.nscl.avatarbuilder.constants.Constants;
import fr.liris.cima.nscl.avatarbuilder.service.AvatarService;

public class AvatarFactory implements AvatarService{

	@Override
	public  Avatar createAvatar(String id, String protocol, String uri ) {
		AvatarManager.createAvatarResources(id, Constants.APOCPATH);
		return new Avatar(id, protocol, uri);
	}

	@Override
	public List<Capability> getCapabilities(String deviceId) {
		return null;
	}
}
