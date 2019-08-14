<template>
  <el-form :label-position="labelPosition" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
    <el-form-item label="T-NUM" prop="ticketNumber">
      <el-input v-model="ruleForm.ticketNumber"></el-input>
    </el-form-item>

    <el-form-item label="标题" prop="ticketTitel">
      <el-input v-model="ruleForm.ticketTitel"></el-input>
    </el-form-item>

    <el-form-item label="描述" prop="description">
      <el-input type="textarea" v-model="ruleForm.description"></el-input>
    </el-form-item>

    <el-row>
      <el-col :span="12">
        <el-form-item label="Ticket版本" prop="vValue">
          <el-select v-model="ruleForm.vValue" multiple placeholder="请选择">
            <el-option v-for="item in version" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
     <!-- <el-col :span="12">
        <el-form-item label="发包版本" prop="version">
          <el-select v-model="vValue" multiple placeholder="请选择">
            <el-option v-for="item in versions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-col> -->
    </el-row>

    <el-form-item label="Deadline" prop="date1" required>
      <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date1" style="width: 100%;"></el-date-picker>
    </el-form-item>

    <el-form-item label="脚本上传" prop="date1">
      <el-upload class="upload-demo" drag action="https://jsonplaceholder.typicode.com/posts/" multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
    </el-form-item>

    <el-form-item label="是否关闭" prop="delivery" required>
      <el-switch v-model="ruleForm.delivery"></el-switch>
    </el-form-item>

    <!--    <el-form-item label="活动性质" prop="type">
      <el-checkbox-group v-model="ruleForm.type">
        <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
        <el-checkbox label="地推活动" name="type"></el-checkbox>
        <el-checkbox label="线下主题活动" name="type"></el-checkbox>
        <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
      </el-checkbox-group>
    </el-form-item> -->
    <!--    <el-form-item label="特殊资源" prop="resource">
      <el-radio-group v-model="ruleForm.resource">
        <el-radio label="线上品牌商赞助"></el-radio>
        <el-radio label="线下场地免费"></el-radio>
      </el-radio-group>
    </el-form-item> -->
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
  export default {

    data() {
      return {
        version: [{//多选数据源
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, ],
        vValue:[],//多选展示用

        labelPosition: "left",
        ruleForm: {
          ticketNumber: '',
          ticketTitel: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
        rules: {
          ticketNumber: [{
              required: true,
              message: '请填写TK号码！',
              trigger: 'blur'
            },
            {
              min: 5,
              max: 5,
              message: '长度在 5 个字符',
              trigger: 'blur'
            }
          ],
          ticketTitel: [{
            required: true,
            message: '请填写标题！',
            trigger: 'blur'
          }],
          vValue: [{
            required: true,
            message: '请选择版本',
            trigger: 'change'
          }],
          date1: [{
            type: 'date',
            required: true,
            message: '请选择日期',
            trigger: 'change'
          }],
          type: [{
            type: 'array',
            required: true,
            message: '请至少选择一个活动性质',
            trigger: 'change'
          }],
          resource: [{
            required: true,
            message: '请选择活动资源',
            trigger: 'change'
          }],
          desc: [{
            required: true,
            message: '请填写活动形式',
            trigger: 'blur'
          }]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style>
  .el-drawer__body {
    overflow: auto;
  }
</style>
