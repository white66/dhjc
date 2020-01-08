package com.rtst.syzx.entity.systemInfo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author leigq
 * @since 2019-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer permissionId;

    private Integer roleId;


}
