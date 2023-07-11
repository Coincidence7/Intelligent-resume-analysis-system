export default {
    state: {
        list: [
            {
                id:     0,
                name:   '',
                gender: '',
                age:    13,
                maxDegree:'',
                maxSchool:'',
                maxMajor: '',
                desiredPostName: '',
                workStartYear: '',
                email:  '',
                phone:  '',
                tags:   [],
                experience: [
                    {
                        title:  '',
                        time:   '',
                        description: '',
                    },
                ],
                predict: [
                    {
                        postId: 0,
                        postName:   '',
                        score:  0.5
                    },
                ],
            },
            {
                id:     1,
                name:   '嫩爹我啊',
                gender: '男',
                age:    25,
                maxDegree:'研究生',
                maxSchool:'白晶耶工业带专',
                maxMajor:  '赛博打灰技术',
                desiredPostName:'打灰总设计师',
                workStartYear:  '-',
                email:      '1145141919@aaa.com',
                phone:      '4080120160',
                tags:       ['nb', '嗯嗯', '儒雅随和'],
                experience: [
                    {
                        title:  '第一个什么鬼经历',
                        time:   '2022-10',
                        description: '项目简介: 对教师上课的语言进行识别，对语言中提及的不同姓名实体进行识别，根据语气分为表扬、批评、鼓励、辱骂、授课五个类别。主要工作如下:● 通过爬虫技术获取校园场景数据、表扬、鼓励、批评数据，通过翻译池对数据增强。②利用 JointBERT 模型思路，将实体识',
                    },
                    {
                        title:  '在这个地方实习还不如...',
                        time:   '2023-05',
                        description: '在湘潭广电中心的时政新闻部实习记者的期间，在实习老师的的指导下初步掌握了相关的新闻记者工作流程，包括采访、摄像、新闻稿的编辑、后期编辑等。并积累了一定的实习经验，完善了所学习的专业知识\\n',
                    },
                ],
                predict:    [
                    // id 为0的为 岗位意愿项 以及匹配度
                    {
                        postId: 0,
                        postName:   '填报岗位',
                        score:  0.4
                    },
                    {
                        postId: 1,
                        postName:   '垃圾岗位',
                        score:  0.4
                    },
                    {
                        postId: 2,
                        postName:   '带专人上人专享职位',
                        score:  0.99
                    },
                    ],
            },
            {
                id:     2,
                name:   '滴东东',
                gender: '男',
                age:    36,
                maxDegree:'博士',
                maxSchool:'集美烤鸭大学',
                maxMajor:  '仗剑攻城时延转业',
                desiredPostName:'大声叫兽',
                workStartYear:  '2011',
                email:      'zqzq@jjj.dud.cn',
                phone:      '14354344454',
                tags:       ['米国访问', '见过世面', '声音很大', '很厉害'],
                experience: [
                    {
                        title:  '很牛皮的故事',
                        time:   '2001-06',
                        description: '自己组队进行了比赛，并在此过程之中进行了市场调研、现状研究、模型分析、创\n新设计、预期成果等\n参与的学校社团有学生会、市场部、勤工助学部等并在期间参与多次活动组织与策划任务',
                    },
                    {
                        title:  '感觉两三个字...',
                        time:   '2013-12',
                        description: '获取底层用户手机本地的相册中的数据集，熟练使用 PhotoKit 和 ALAssetsLibrary 来进行用户本地\\n图片视频集的获取，并且根据每个图片自身的信息不同进行重新划分，例如是否使用过美图秀秀等这一类\n的图片美化软件修过的图片，以及不同应用接受保存或者创建的图片，同时进行速率优化，在用户的手机',
                    },
                    {
                        title:  '我大痛实太好吃辣',
                        time:   '2017-09',
                        description: '在一个月的培训中，我熟练掌握了怎么烧一顿好吃的泡面在一个月的培训中，我熟练掌握了怎么烧一顿好吃的泡面在一个月的培训中，我熟练掌握了怎么烧一顿好吃的泡面',
                    },
                    {
                        title:  '很牛皮事',
                        time:   '2018-01',
                        description: '自己组队进行了比赛，并在此过程之中进行了市场调研、现状研究、模型分析、创\n新设计、预期成果等\n参与的学校社团有学生会、市场部、勤工助学部等并在期间参与多次活动组织与策划任务',
                    },
                    {
                        title:  '感觉不如两个字的三个字...',
                        time:   '2017-12',
                        description: '获取底层用户手机本地的相册中的数据集，熟练使用 PhotoKit 和 ALAssetsLibrary 来进行用户本地\\n图片视频集的获取，并且根据每个图片自身的信息不同进行重新划分，例如是否使用过美图秀秀等这一类\n的图片美化软件修过的图片，以及不同应用接受保存或者创建的图片，同时进行速率优化，在用户的手机',
                    },
                    {
                        title:  '我闷大使痛实在太好吃辣',
                        time:   '2020-03',
                        description: '在一个月的培训中，我熟练掌握了怎么烧一顿好吃的泡面在一个月的培训中，我熟练掌握了怎么烧一顿好吃的泡面在一个月的培训中，我熟练掌握了怎么烧一顿好吃的泡面',
                    },
                ],
                predict:    [
                    // id 为0的为 岗位意愿项 以及匹配度
                    {
                        postId: 0,
                        postName:   '带式凶凶',
                        score:  0.2
                    },
                    {
                        postId: 1,
                        postName:   '乐乐乐乐',
                        score:  0.8
                    },
                    {
                        postId: 2,
                        postName:   '结构体系僵尸',
                        score:  0.76
                    },
                    {
                        postId: 3,
                        postName:   '芜湖呜呼呜呼',
                        score:  0.3
                    },
                    {
                        postId: 4,
                        postName:   '带式凶凶',
                        score:  0.2
                    },
                    {
                        postId: 5,
                        postName:   '乐乐乐乐',
                        score:  0.8
                    },
                    {
                        postId: 6,
                        postName:   '结构体系僵尸',
                        score:  0.76
                    },
                    {
                        postId: 7,
                        postName:   '芜湖呜呼呜呼',
                        score:  0.3
                    },
                ],
            }
            ],
    },
    getters: {},
    mutations: {
        getAnalyseResult(state, data) {
            state.list = data.list;
        },
        setSelectedIndex(state, index) {
            state.selectIndex = index;
        },
    },
    actions: {

    },
    modules: {}
}
