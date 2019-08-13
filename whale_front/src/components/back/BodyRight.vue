<template>
  <div>
    <el-button-group>
      <el-button size="small" type="primary" icon="el-icon-circle-plus-outline" @click="drawer = true"></el-button>
      <el-button type="primary" icon="el-icon-share"></el-button>
      <el-button type="primary" icon="el-icon-delete"></el-button>
    </el-button-group>
    <el-drawer title="增加Ticket" :visible.sync="drawer" :before-close="handleClose" size="50%">
      <AddForm style="padding: 10px;"></AddForm>
    </el-drawer>
    <data-tables-server :data="data" @current-page-change="handleCurrentPageChange" @current-change="handleCurrentChange"
      @prev-click="handlePrevClick" @size-change="handleSizeChange" @selection-change="handleSelectionChange"
      :current-page.sync="currentPage" :page-size="pageSize" :total="total" :pagination-props="{ pageSizes: [5, 10, 15] }"
      :action-col="actionCol">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column show-overflow-tooltip v-for="title in titles" :prop="title.prop" :label="title.label" :key="title.prop"></el-table-column>
    </data-tables-server>
  </div>
</template>

<script>
  import AddForm from '@/components/back/AddForm';
  export default {
    components: {
      AddForm
    },
    mounted: function() { //页面初始化调用
      this.request();
    },
    data() {
      return {
        total: 0,
        currentPage: 1,//当前页，后台需要减一
        pageSize: 5,

        drawer: false,
        workData: [],
        data: [],
        // titles: [],
        titles: [{
            prop: 'ticketNumber',
            label: 'T-NUM',
            type: Number,
            // order: 'descending'
          },
          {
            prop: 'ticketTitel',
            label: '标题',
            width: '100px'

            // show-overflow-tooltip="true"
          },
          {
            prop: 'patch',
            label: '发包版本'
          },
          {
            prop: 'version',
            label: 'TK版本'
          },
          {
            prop: 'deadline',
            label: 'Deadline'
          },
          {
            prop: 'isExample',
            label: '用例'
          },
          {
            prop: 'sqlurl_font',
            label: '脚本'
          },
          {
            prop: 'isClose',
            label: '状态'
          }
        ],
        actionCol: {
          label: '操作',
          props: {
            align: 'center',
            width: '150px'
          },
          buttons: [{
              props: {
                type: 'primary',
                icon: 'el-icon-edit'
              },
              handler: row => {
                this.$message('Edit clicked');
                row.flow_no = 'hello word' + Math.random();
                row.content = Math.random() > 0.5 ? 'Water flood' : 'Lock broken';
                row.flow_type = Math.random() > 0.5 ? 'Repair' : 'Help';
              }
              // label: '编辑'
            },
            {
              props: {
                type: 'primary',
                icon: 'el-icon-delete-solid'
              },
              handler: row => {
                this.$confirm('确认删除？')
                  .then(_ => {
                    this.$message({
                      type: 'success',
                      message: row.ticketNumber + '删除成功!'
                    });
                    this.data.splice(this.data.indexOf(row), 1);
                  })
                  .catch(_ => {
                    this.$message({
                      type: 'info',
                      message: '已取消删除'
                    });
                  });
              }
              // label: '删除'
            }
          ]
        }
      };
    },
    methods: {
      request() {
        this.axios
          .get('http://localhost:8080/whale/api/work/queryInfo', {
            params: {
              page: this.currentPage,
              pageSize: this.pageSize,

            }
          })
          .then(response => {
            console.log(response.data);
            this.data = response.data.content;
            this.total = response.data.totalElements;
            // this.currentPage = 2;
            // this.pageSize = 1;
            // let arr = response.data;
            // for (let i in arr) {
            //   if(i == 0){
            //     for (let info in arr[i]) {
            //       let tk = new Object();
            //       tk.prop = info;
            //       tk.label = info;
            //       this.titles.push(tk);
            //     }
            //   }
            // }
            // console.log(this.titles);
          })
          .catch(response => {});
      },
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      handleCurrentPageChange(page) {
        this.request();
        this.$notify({
          message: `pagination current-change: ${page}`
        });
      },
      handleCurrentChange(currentRow) {
        // alert(1)
        this.$notify({
          message: `el-table current-change: ${currentRow.ticketNumber}`
        });
      },
      handlePrevClick(page) {
        this.$notify({
          message: `prev-click: ${page}`
        });
      },
      handleSizeChange(size) {
        this.$notify({
          message: `size-change: ${size}`
        });
      },
      handleSelectionChange(val) {
        this.$notify({
          message: `selection-change: ${val.map(row => row.ticketNumber).join(',')}`
        });
      }
    }
  };
</script>
<style>
</style>
