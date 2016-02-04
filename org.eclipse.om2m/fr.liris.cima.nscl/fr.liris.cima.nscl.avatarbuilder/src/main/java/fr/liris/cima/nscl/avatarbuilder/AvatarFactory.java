package fr.liris.cima.nscl.avatarbuilder;

import java.util.List;

import fr.liris.cima.nscl.avatarbuilder.constants.Constants;
import fr.liris.cima.nscl.avatarbuilder.service.AvatarService;

/**
 * Implementation of AvatarService.
 */
public class AvatarFactory implements AvatarService{

	/**
	 * Create an avatar by giving an ID, protocol and URI ressources
	 * @param id avatar ID
	 * @param protocol the protocol used by the avatar
	 * @param uri URI ressource
	 * @return a new avatar
	 */
	@Override
	public  Avatar createAvatar(String id, String protocol, String uri ) {
		AvatarManager.createAvatarResources(id, Constants.APOCPATH);
		return new Avatar(id, protocol, uri);
	}

	/**
	 * Get list of capabilities for a device
	 * @param deviceId the device ID
	 * @return a list of capabilities
	 */
	@Override
	public List<Capability> getCapabilities(String deviceId) {
		return null;
	}
}
