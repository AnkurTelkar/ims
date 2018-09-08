package com.ims.user.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.ims.ApplicationConstants;
import com.ims.user.dto.UserDto;
import com.ims.user.entity.UserDetailsEntity;
import com.ims.user.entity.UserEntity;
import com.ims.user.repository.UserRepository;

@Service("userService")
public class UserService implements UserDetailsService{
	
	Logger logger = LoggerFactory.getLogger( this.getClass().getName() );

	private UserRepository userRepository;

	private TypeToken<List<UserDto>> typeToken = new TypeToken<List<UserDto>>() {
		private static final long serialVersionUID = 1L;
	};

	public Integer findMaxIdForUser() {
		return userRepository.findByUserIdMax();
	}

	public List<UserDto> getAllUsers() {
		List<UserEntity> userEntityList = (List<UserEntity>) userRepository.findAll();
		List<UserDto> userDtoList = entityToDtoList(userEntityList);
		return userDtoList;
	}

	public UserEntity save(UserDto userDto) {
		UserEntity userEntity = dtoToEntity(userDto);
		userEntity.setEmail(userEntity.getEmail().toLowerCase());
		userEntity.setCreationTime(new Date());
		return userRepository.save(userEntity);
	}

	public String getUserCode() {
		String uuid = UUID.randomUUID().toString().substring(0, 4);
		
		UserEntity userEntity = userRepository.findByUserCode(uuid);
		try{
			 if(!(userEntity.equals(null))){
				 this.getUserCode();
			 }
		}
		catch(Exception exception){
			return uuid;
		}
		return uuid;
	}

	public boolean checkEmailAvailability(UserDto userDto) {
		
		UserEntity userEntity = userRepository.findByEmail(userDto.getEmail());
		try{
			if(!(userEntity.equals(null))){
				if(userEntity.getUserId() == userDto.getUserId()){
					return true;
				}
				return false;				
			}
		}
		catch(Exception exception){
			return true;
		}		
		return false;
	}

	public boolean chckLoginIdAvailability(UserDto userDto) {
		
		UserEntity userEntity = userRepository.findByLoginId(userDto.getLoginId());
		try{
			if(!(userEntity.equals(null))){
				if(userEntity.getUserId() == userDto.getUserId()){
					return true;
				}
				else{
					return false;
				}
			}
		}
		catch(Exception exception){
			return true;
		}
		return false;
		/*List<UserEntity> availableLoginIdList = userRepository.findByLoginId(userDto.getLoginId());
		if (availableLoginIdList.size() == 0) {
			return true;
		}

		else if (availableLoginIdList.size() == 1) {
			if (availableLoginIdList.get(0).getUserId() == userDto.getUserId()) {
				return true;
			}
			return false;
		}

		else {
			return false;
		}*/
	}

	public boolean checkUserCodeAvailability(UserDto userDto) {
		
		UserEntity userEntity = userRepository.findByUserCode(userDto.getUserCode());
		try{
			if(!(userEntity).equals(null)){
				if(userEntity.getUserId() == userDto.getUserId()){
					return true;
				}
			}
			else{
				return false;
			}
		}
		catch(Exception exception){
			return true;
		}
				
		return false;
		
		
		
		
		/*List<UserEntity> availableUserList = userRepository.findByUserCode(userDto.getUserCode());	
		
		if (availableUserList.size() == 0) {
			return true;
		}

		else if (availableUserList.size() == 1) {
			if (availableUserList.get(0).getUserId() == userDto.getUserId()) {
				return true;
			}
			return false;
		}

		else {
			return false;
		}*/
	}

	public UserDto findUserByUserId(Integer userId) {
		//List<UserEntity> userEntityByUserId = userRepository.findByUserId(userId);
		UserDto userDto=null;
		try{
			UserEntity userEntity = userRepository.findOne(userId);
			if(!(userEntity).equals(null)){
				userDto = this.entityToDto(userEntity);
				return userDto;
			}		
		}
		catch(Exception exception){
			return null;
		}
		return userDto;
	}

	public List<UserDto> entityToDtoList(List<UserEntity> userEntityList) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userEntityList, typeToken.getType());
	}

	public UserEntity dtoToEntity(UserDto userDto) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userDto, UserEntity.class);
	}

	public UserDto entityToDto(UserEntity userEntity) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userEntity, UserDto.class);
	}

	public boolean inactiveUserById(Integer userId) {
		try {
			UserEntity entity = userRepository.findOne(userId);
			entity.setStatus(ApplicationConstants.INACTIVE);
			userRepository.save(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateUser(UserDto userDto) {
		UserEntity entity = dtoToEntity(userDto);
		UserEntity savedEntity = userRepository.save(entity);
		return savedEntity.getUserId() > 0;
	}

	public boolean doLogin(String loginId, String password) {
		try {
			UserEntity userEntity = userRepository.findByLoginIdAndPasswordAndStatus(loginId, password);
			if (!(userEntity.equals(null))) {
				return true;
			}
		} catch (Exception exception) {
			return false;
		}
		return true;
	}
	
	public UserDto findByLoginId( String loginId )  {
		UserEntity userEntity = userRepository.findByLoginId(loginId);
		if( userEntity == null ) {
			return null;
		}
		return entityToDto(userEntity);
	}
	
	public UserDto findByUserCode( String userCode )  {
		UserEntity userEntity = userRepository.findByUserCode(userCode);
		if( userEntity == null ) {
			return null;
		}
		return entityToDto(userEntity);
	}

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		UserEntity userEntity=userRepository.findByLoginId(loginId);
		return new UserDetailsEntity(userEntity);
	}
	
	@Autowired
    public UserService(UserRepository userRepository) {
		logger.debug("in UserService ---  UserService() ");
        this.userRepository = userRepository;
    }

}