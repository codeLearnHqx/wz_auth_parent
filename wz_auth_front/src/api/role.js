import request from "@/utils/request";

const baseURI = "/admin/system/sysRole";

// 列表请求
export function getPageList(current, size, searchObj) {
  return request({
    url: `${baseURI}/page/${current}/${size}`,
    method: "get",
    params: searchObj,
  });
}

// 删除请求
export function removeById(id) {
  return request({
    url: `${baseURI}/remove/${id}`,
    method: "delete",
  });
}

// 批量删除请求
export function removeBatchByIds(ids) {
  return request({
    url: `${baseURI}/batchRemove`,
    method: "delete",
    data: ids,
  });
}

// 添加请求
export function saveRole(role) {
  return request({
    url: `${baseURI}/save`,
    method: "post",
    data: role,
  });
}

// 根据id获取
export function getRole(id) {
  return request({
    url: `${baseURI}/findRoleById/${id}`,
    method: "get",
  });
}

// 修改请求
export function updateRole(role) {
  return request({
    url: `${baseURI}/update`,
    method: "post",
    data: role,
  });
}

//根据用户id查询用户已分配的角色
export function getRolesByUserId(userId) {
  return request({
    url: `${baseURI}/toAssign/${userId}`,
    method: "get",
  });
}

//分配角色
export function assignRoles(assginRoleVo) {
  return request({
    url: `${baseURI}/doAssign`,
    method: "post",
    data: assginRoleVo,
  });
}
