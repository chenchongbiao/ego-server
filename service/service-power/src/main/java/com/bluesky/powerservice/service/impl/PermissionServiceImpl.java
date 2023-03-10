package com.bluesky.powerservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bluesky.powerservice.entity.Permission;
import com.bluesky.powerservice.entity.RolePermission;
import com.bluesky.powerservice.entity.User;
import com.bluesky.powerservice.helper.MemuHelper;
import com.bluesky.powerservice.helper.PermissionHelper;
import com.bluesky.powerservice.mapper.PermissionMapper;
import com.bluesky.powerservice.service.PermissionService;
import com.bluesky.powerservice.service.RolePermissionService;
import com.bluesky.powerservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.bluesky.powerservice.helper.PermissionHelper.bulid;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserService userService;
    
    //获取全部菜单
    @Override
    public List<Permission> queryAllMenu() {

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);

        List<Permission> result = bulid(permissionList);

        return result;
    }

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = bulid(allPermissionList);
        return permissionList;
    }

    //给角色分配菜单
    @Override
    public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {
        // role角色id
        // permissionId菜单id 数组形式
        // 创建list集合封装添加数据
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        List<RolePermission> rolePermissionList = new ArrayList<>();
        // 遍历所有菜单数组
        for(String permissionId : permissionIds) {
            if(StringUtils.isEmpty(permissionId)) continue;
            // RolePermission对象
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            // 封装到list集合
            rolePermissionList.add(rolePermission);
        }
        // 添加到角色菜单关系表
        rolePermissionService.saveBatch(rolePermissionList);
    }

    //递归删除菜单
    @Override
    public void removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);

        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        System.out.println("菜单："+selectPermissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>().eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
//    private static List<Permission> bulid(List<Permission> treeNodes) {
//        List<Permission> trees = new ArrayList<>();
//        for (Permission treeNode : treeNodes) {
//            if ("0".equals(treeNode.getPid())) {
//                treeNode.setLevel(1);
//                trees.add(findChildren(treeNode,treeNodes));
//            }
//        }
//        return trees;
//    }

    /**
     * 递归查找子节点
     * @return
     */
//    private static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
//        treeNode.setChildren(new ArrayList<Permission>());
//
//        for (Permission it : treeNodes) {
//            if(treeNode.getId().equals(it.getPid())) {
//                int level = treeNode.getLevel() + 1;
//                it.setLevel(level);
//                if (treeNode.getChildren() == null) {
//                    treeNode.setChildren(new ArrayList<>());
//                }
//                treeNode.getChildren().add(findChildren(it,treeNodes));
//            }
//        }
//        return treeNode;
//    }




    //把返回所有菜单list集合进行封装的方法
    public static List<Permission> bulidPermission(List<Permission> permissionList) {

        //创建list集合，用于数据最终封装
        List<Permission> finalNode = new ArrayList<>();
        //把所有菜单list集合遍历，得到顶层菜单 pid=0菜单，设置level是1
        for(Permission permissionNode : permissionList) {
            //得到顶层菜单 pid=0菜单
            if("0".equals(permissionNode.getPid())) {
                //设置顶层菜单的level是1
                permissionNode.setLevel(1);
                //根据顶层菜单，向里面进行查询子菜单，封装到finalNode里面
                finalNode.add(selectChildren(permissionNode,permissionList));
            }
        }
        return finalNode;
    }

    private static Permission selectChildren(Permission permissionNode, List<Permission> permissionList) {
        //1 因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        permissionNode.setChildren(new ArrayList<Permission>());

        //2 遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for(Permission it : permissionList) {
            //判断 id和pid值是否相同
            if(permissionNode.getId().equals(it.getPid())) {
                //把父菜单的level值+1
                int level = permissionNode.getLevel()+1;
                it.setLevel(level);
                //如果children为空，进行初始化操作
                if(permissionNode.getChildren() == null) {
                    permissionNode.setChildren(new ArrayList<Permission>());
                }
                //把查询出来的子菜单放到父菜单里面
                permissionNode.getChildren().add(selectChildren(it,permissionList));
            }
        }
        return permissionNode;
    }




}
