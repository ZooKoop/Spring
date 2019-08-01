<template>
  <div>
    <el-button-group>
      <el-button size="small" type="primary" icon="el-icon-circle-plus-outline" @click="drawer = true"></el-button>
      <el-button type="primary" icon="el-icon-share"></el-button>
      <el-button type="primary" icon="el-icon-delete"></el-button>
    </el-button-group>
    <el-drawer title="增加Tickt" :visible.sync="drawer" :before-close="handleClose" size="50%">
      <AddForm style="padding: 10px;"></AddForm>
    </el-drawer>
    <data-tables :data="data" :action-col="actionCol">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column v-for="title in titles" :prop="title.prop" :label="title.label" :key="title.prop">
      </el-table-column>
    </data-tables>
  </div>

</template>

<script>
  import AddForm from '@/components/back/AddForm';
  export default {
    components:{AddForm},
    data() {
      return {
        drawer: false,
        data: [{
          'BH': "1",
          'NM': "kaka"
        }, {
          'BH': "1",
          'NM': "kaka"
        }],
        titles: [{}, {
          prop: "BH",
          label: "编号",
        }, {
          prop: "NM",
          label: "名字"
        }],
        actionCol: {
          label: '操作',
          props: {
            align: 'center',
          },
          buttons: [{
            props: {
              type: 'primary',
              icon: 'el-icon-edit'
            },
            handler: row => {
              this.$message('Edit clicked')
              row.flow_no = 'hello word' + Math.random()
              row.content = Math.random() > 0.5 ? 'Water flood' : 'Lock broken'
              row.flow_type = Math.random() > 0.5 ? 'Repair' : 'Help'
            },
            label: '编辑'
          }, {
            props: {
              type: 'primary',
              icon: 'el-icon-delete-solid'
            },
            handler: row => {
              this.data.splice(this.data.indexOf(row), 1)
            },
            label: '删除'
          }]
        }
      }
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      }
    },
  }
</script>
<style>
</style>
