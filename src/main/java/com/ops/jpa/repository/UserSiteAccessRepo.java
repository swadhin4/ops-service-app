package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ops.jpa.entities.UserSiteAccess;

public interface UserSiteAccessRepo extends JpaRepository<UserSiteAccess, Long> {

	@Query("from UserSiteAccess usa where usa.user.userId=:userId order by usa.site.siteName")
	public List<UserSiteAccess> findSiteAssignedFor(@Param(value="userId") Long userId);

	@Query("from UserSiteAccess usa where usa.user.userId=:userId and usa.site.siteId=:siteId")
	public UserSiteAccess findAccessDetails(@Param(value="userId") Long userId, @Param(value="siteId") Long siteId);

	@Query("from UserSiteAccess usa where usa.site.siteId=:siteId order by usa.site.siteName")
	public List<UserSiteAccess> findUserAssignedFor(@Param(value="siteId") Long siteId);

	@Query(value= "select access_id,user_id from pm_user_access where site_id=:siteId", nativeQuery=true)
	public List<Object[]> findUserAccessList(@Param(value="siteId") Long siteId);

	@Query(value= "select access_id,site_id from pm_user_access where user_id=:userId", nativeQuery=true)
	public List<Object[]> findSiteAccessList(@Param(value="userId") Long userId);

}
