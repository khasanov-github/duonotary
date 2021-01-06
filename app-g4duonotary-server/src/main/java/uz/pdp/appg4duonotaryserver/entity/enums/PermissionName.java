package uz.pdp.appg4duonotaryserver.entity.enums;

import java.util.Arrays;
import java.util.List;

public enum PermissionName {

    MANAGE_ADMINS(Arrays.asList(RoleName.ROLE_SUPER_ADMIN), "Manage Admin"),
    GET_LOGS(Arrays.asList(RoleName.ROLE_SUPER_ADMIN), "GET LOGS"),
    MANAGE_AGENTS(Arrays.asList(RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_ADMIN), "Manage Agent"),


    //Agent MANAGE
    ADD_AGENTS(Arrays.asList(RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_ADMIN), "Manage Agent"),
    EDIT_AGENTS(Arrays.asList(RoleName.ROLE_SUPER_ADMIN, RoleName.ROLE_ADMIN,RoleName.ROLE_AGENT), "Manage Agent");


    public List<RoleName> roleNames;

    public String name;

    PermissionName(List<RoleName> roleNames, String name) {
        this.roleNames = roleNames;
        this.name = name;
    }
}
