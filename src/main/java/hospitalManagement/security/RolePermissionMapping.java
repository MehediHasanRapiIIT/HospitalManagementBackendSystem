package hospitalManagement.security;


import hospitalManagement.entity.type.PermissionType;
import hospitalManagement.entity.type.RoleType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static hospitalManagement.entity.type.PermissionType.*;
import static hospitalManagement.entity.type.RoleType.*;

public class RolePermissionMapping {

    private static final Map<RoleType, Set<PermissionType>> map = Map.of(
            PATIENT,Set.of(PATIENT_READ, APPOINTMENT_READ, APPOINTMENT_WRITE),
            DOCTOR, Set.of(APPOINTMENT_DELETE, APPOINTMENT_READ, APPOINTMENT_WRITE, PATIENT_READ),
            ADMIN, Set.of(PATIENT_READ, PATIENT_WRITE, APPOINTMENT_READ, APPOINTMENT_WRITE, APPOINTMENT_DELETE, USER_MANAGE, REPORT_VIEW)
    );

    public static Set<SimpleGrantedAuthority> getAthoritiesByRole(RoleType role) {
        return map.get(role)
                .stream()
                .map(permission-> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(java.util.stream.Collectors.toSet());
    }
}
