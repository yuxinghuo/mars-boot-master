const uitls = {
    state: {
        screenWidth: 200,
        menuImgCur: true,
        // 方法一
        // thisParent: {},
        // everyThat: '',
        // thatList: [],
        // thatList: {},
        // 路由信息

        // 方法二
        keepAliveList: [],
        // 切换部门  换了provide方法
        // department: false,
    },
    mutations: {
        SET_SCREENWIDTH:(state, fixed)=>{
            state.screenWidth = fixed
        },
        SET_REDACTMENUIMGCUR:(state,fixed)=>{
            state.menuImgCur =  fixed
        },
        // 方法一
        // SET_CHANGETHISPARENT:(state,{cache,that})=>{
        //     state.thisParent = cache
        //     state.everyThat = that
            
        //     state.thatList.push(that)
        //     console.log(state.thatList)
        // },
        // 方法二
        // 路由信息List方法
        // push
        SETPUSH_CHANGEKEEPALIVELIST:(state,fixed)=>{
            state.keepAliveList.push(fixed)
            state.keepAliveList = [...new Set(state.keepAliveList)]
        },
        // 更换数组
        SET_CHANGEKEEPALIVELIST:(state,fixed)=>{
            console.log(fixed)
            state.keepAliveList = [...new Set(fixed)]
            console.log(state.keepAliveList,'组件地址更换')
            // if(state.keepAliveList.length === 1 && (state.keepAliveList[0] === 'analysis' || state.keepAliveList[0] === 'Analysis')){
            //     console.log('shuzukongle')
            //     state.keepAliveList = []
            // }else{
                
            // }
        },
        //切换部门   换了provide方法
        // SET_CHANGEDEPARTMENT:(state,fixed)=>{
        //     console.log(state,'333333333')
        //     state.department = !state.department
        // }
    },
    actions: {
        // 宽度
        TOSET_SCREENWIDTH({commit},value){
            commit('SET_SCREENWIDTH',value)
        },
        // menu 的菜单模式
        TOSET_REDACTMENUIMGCUR({commit},value){
            commit('SET_REDACTMENUIMGCUR',value)
        },
        // 清缓存
        // TOSET_CHANGETHISPARENT({commit},{cache,that}){
        //     console.log(cache,that)
        //     commit('SET_CHANGETHISPARENT',{cache,that})
        // },
        // 路由信息List方法
        TOSETPUSH_CHANGEKEEPALIVELIST({commit},value){
            commit('SETPUSH_CHANGEKEEPALIVELIST',value)
        },
        TOSET_CHANGEKEEPALIVELIST({commit},value){
            commit('SET_CHANGEKEEPALIVELIST',value)
        },
        // 切换部门   换了provide方法
        // TOSET_CHANGEDEPARTMENT({commit},value){
        //     console.log(value,'222222')
        //     commit('SET_CHANGEDEPARTMENT',value)
        // }

    }
}
export default uitls