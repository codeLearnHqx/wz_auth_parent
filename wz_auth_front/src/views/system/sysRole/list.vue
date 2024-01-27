<template>
  <div id="container">
    <el-card>
      <!--查询表单-->
      <div>
        <el-form label-width="70px" size="small">
          <el-row>
            <el-col :span="8">
              <el-form-item label="角色名称">
                <el-input v-model.trim="searchObj.roleName" style="width: 100%" placeholder="角色名称"/>
              </el-form-item>
            </el-col>
            <el-col :span="4" style="display:flex; margin-left: 10px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="search">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <!-- 工具条 -->
      <div>
        <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>
        <el-button class="btn-add" size="mini" @click="batchRemove()">批量删除</el-button>
      </div>
      <!-- 表格 -->
      <el-table
        v-loading="listLoading"
        :data="list"
        stripe
        border
        style="width: 100%;margin-top: 10px;"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection"/>
        <el-table-column
          label="序号"
          width="70"
          align="center"
        >
          <template slot-scope="scope">
            {{ (current - 1) * size + scope.$index + 1 }}
          </template>
        </el-table-column>

        <el-table-column prop="roleName" label="角色名称"/>
        <el-table-column prop="roleCode" label="角色编码"/>
        <el-table-column prop="createTime" label="创建时间" width="160"/>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button
              title="修改"
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="edit(scope.row.id)"
            />
            <el-button
              title="删除"
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeDataById(scope.row.id)"
            />
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 分页组件 -->
    <el-pagination
      style="text-align: center; margin-top: 10px"
      :current-page="current"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" :model="sysRole" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="角色名称">
          <el-input v-model="sysRole.roleName"/>
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input v-model="sysRole.roleCode"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getPageList, getRole, removeBatchByIds, removeById, saveRole, updateRole } from '@/api/role'

export default {
  data() {
    return {
      listLoading: false, // 数据是否正在加载
      current: 1,
      size: 5,
      searchObj: {}, // 条件查询对象
      list: [], // 角色列表
      total: 0, // 总记录数
      sysRole: {}, // 表单数据
      dialogVisible: false,
      saveBtnDisabled: false,
      multipleSelection: [] // 批量删除选中的记录列表
    }
  },
  created() {
    this.fetchData(this.current, this.size, this.searchObj)
  },
  methods: {
    // 请求分页数据
    async fetchData(current, size, searchObj) {
      this.listLoading = true
      try {
        const res = await getPageList(current, size, searchObj)
        if (res.code === 200) {
          this.total = res.data.total
          this.list = res.data.records
        }
      } catch (e) {
        this.$message.error('请求数据失败')
      } finally {
        this.listLoading = false
      }
    },
    flushPage(searObj) {
      this.fetchData(this.current, this.size, searObj)
    },
    // 编辑
    async edit(id) {
      this.dialogVisible = true
      const res = await getRole(id)
      if (res.code === 200) {
        this.sysRole = res.data
      }
    },
    // 删除
    removeDataById(id) {
      this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        try {
          const res = await removeById(id)
          if (res.code === 200) {
            await this.flushPage(this.searchObj)
            this.$message.success('删除成功')
          } else {
            this.$message.error('删除失败')
          }
        } catch (e) {
          this.$message.error('删除失败')
        }
      })
    },
    handleSizeChange(val) {
      this.size = val
      this.flushPage(this.searchObj)
    },
    handleCurrentChange(val) {
      this.current = val
      this.flushPage(this.searchObj)
    },
    // 重置表单
    resetData() {
      this.searchObj = {}
      this.current = 1
      this.flushPage(this.searchObj)
    },
    search() {
      this.current = 1
      this.flushPage(this.searchObj)
    },
    // 弹出添加的表单
    add() {
      this.dialogVisible = true
      this.sysRole = {}
    },
    // 添加或更新
    saveOrUpdate() {
      if (!this.sysRole.id) {
        this.save()
      } else {
        this.update()
      }
    },
    // 添加
    async save() {
      try {
        const res = await saveRole(this.sysRole)
        if (res.code === 200) {
          this.$message.success('添加角色成功')
          // 刷新
          this.current = 1
          this.flushPage(null)
        } else {
          this.$message.error('添加角色失败')
        }
      } catch (e) {
        this.$message.error('添加角色失败')
        console.log(e)
      } finally {
        this.dialogVisible = false
      }
    },
    // 修改
    async update() {
      try {
        const res = await updateRole(this.sysRole)
        if (res.code === 200) {
          this.$message.success('修改角色成功')
          // 刷新
          this.current = 1
          this.flushPage(null)
        } else {
          this.$message.error('修改角色失败')
        }
      } catch (e) {
        console.log(e)
        this.$message.error('修改角色失败')
      } finally {
        this.dialogVisible = false
      }
    },
    // 批量删除
    async batchRemove() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning('请选中需要删除的数据')
        return
      }
      this.$confirm('此操作将删除选中的所有数据, 是否继续?', '提示', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        try {
          const res = await removeBatchByIds(this.multipleSelection)
          if (res.code === 200) {
            this.$message.success('批量删除成功')
            // 刷新
            this.flushPage(this.searchObj)
          }
        } catch (e) {
          console.log(e)
          this.$message.error('批量删除失败')
        }
      })
    },
    // 多选框触发事件
    handleSelectionChange(val) {
      this.multipleSelection = val.map(item => {
        return item.id
      })
    }
  }
}
</script>
<style lang="sass" scoped>
#container
  padding: 10px

</style>
