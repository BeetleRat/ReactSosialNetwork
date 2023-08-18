import React from "react";

class ProfileStatusClassComponent extends React.PureComponent {
    state = {
        editMode: false,
        status: this.props.status
    }

    // componentDidUpdate(prevProps, prevState, snapshot) {
    //     if (this.state !== prevState) {
    //
    //     }
    // }

    editModeON() {
        this.setState(
            {
                editMode: true
            }
        );
    }

    editModeOFF() {
        this.setState(
            {
                editMode: false
            }
        );

        this.props.updateStatus(this.props.authUserID, this.state.status);
    }

    changeInputTextStatus(event) {
        let status = event.target.value;

        return this.props.setStatus(status);
    }

    render() {
        return (
            <div>
                {
                    this.state.editMode
                        ? <div>
                            <label htmlFor="statusInput">Статус: </label>
                            <input id="statusInput" name="statusInput" type="text" value={this.props.status}
                                   onChange={this.changeInputTextStatus.bind(this)} onBlur={this.editModeOFF.bind(this)}
                                   autoFocus={true}/>
                        </div>
                        : <div>
                            <p name="status"
                               onDoubleClick={this.editModeON.bind(this)}>Статус: {this.props.status ? this.props.status : "No status."}</p>
                        </div>
                }

            </div>
        );
    }
}

export default ProfileStatusClassComponent;