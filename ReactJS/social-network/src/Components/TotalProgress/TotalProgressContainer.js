import TotalProgress from "./TotalProgress";
import {connect} from "react-redux";
import {AddNewTask, ChangeCurrentMinutes} from "../../Redux/Redusers/TotalProgressReducer";
import {getTasks} from "../../Redux/Selectors/ProgressSelectors";

let MapStateToProps=(state)=>{
    return{
        tasks: getTasks(state)
    }
}

export default connect(MapStateToProps,{ChangeCurrentMinutes,AddNewTask})(TotalProgress);