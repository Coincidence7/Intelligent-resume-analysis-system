export default{
    state:{         
        list: [{
            index: '1',
            filename: "待定",
            type: "待定",
            stage: "doing",
            time: 0
        }],
        finish_num: 0,
        interval: [

        ],
        stop_flag: false
    },
    getters: {},
    mutations:{
        updateStopFlag(state){
            state.stop_flag = true
        },
        updateResumeRecord(state, data){
            state.list = data.list
        },
        updateOneResumeRecord(state, data){
            if(!state.stop_flag){
                clearInterval(state.interval[data.idx])
                let idx_before = state.list[data.idx].index
                let time_before = state.list[data.idx].time
                state.list[data.idx] = data.element
                state.list[data.idx].index = idx_before
                state.list[data.idx].time = time_before
                state.finish_num ++;
            }
            
        },
        updateTime(state, data){
            state.list[data.idx].time ++;
        },
        stopParse(state){
            state.stop_flag = true
            for(let i = 0; i < state.list.length; i++){
                if(state.list[i].stage == 'doing' || state.list[i].stage == 'todo'){
                    state.list[i].stage = 'stopped'
                }
                clearInterval(state.interval[i])
            }
        },
        addInterval(state, data){
            state.interval[data.idx] = data.interval
        }
    },
    actions:{
    },
    modules:{}
}