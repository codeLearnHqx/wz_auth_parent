import request from "@/utils/request";

const baseURI = "/admin/system/sysUser";

// 列表请求
export function getPageList(current, size, searchObj) {
  return request({
    url: `${baseURI}/${current}/${size}`,
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

// 添加请求
export function saveUser(user) {
  return request({
    url: `${baseURI}/save`,
    method: "post",
    data: user,
  });
}

// 根据id获取
export function getUser(id) {
  return request({
    url: `${baseURI}/getUser/${id}`,
    method: "get",
  });
}

// 修改请求
export function updateUser(user) {
  return request({
    url: `${baseURI}/update`,
    method: "post",
    data: user,
  });
}
// 更改用户状态
export function updateUserStatus(id, status) {
  return request({
    url: `${baseURI}/updateStatus/${id}/${status}`,
    method: "get",
  });
}

// 登录
export function login(data) {
  return request({
    url: "/admin/system/index/login",
    method: "post",
    data,
  });
}

// 获取用户信息
export function getInfo(token) {
  return request({
    url: "/admin/system/index/info",
    method: "get",
    params: { token },
  });
}

// 退出
export function logout() {
  return request({
    url: "/vue-admin-template/user/logout",
    method: "post",
  });
}
