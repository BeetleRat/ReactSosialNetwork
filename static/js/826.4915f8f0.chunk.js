"use strict";(self.webpackChunksocial_network=self.webpackChunksocial_network||[]).push([[826],{7929:function(e,n,s){s.d(n,{D:function(){return l}});var r=s(1413),t=s(5987),i=s(2791),a=s(8687),c=s(4895),u=s(7689),d=s(184),o=["isAuth"];function l(e){var n=i.memo((function(n){var s=n.isAuth,i=(0,t.Z)(n,o);return s?(0,d.jsx)(e,(0,r.Z)({},i)):(0,d.jsx)(u.Fg,{to:"/login"})}));return(0,a.$j)((function(e){return{isAuth:(0,c.Od)(e)}}),{})(n)}},3826:function(e,n,s){s.r(n),s.d(n,{default:function(){return m}});var r=s(3433),t=s(9439),i=s(2791),a=s(6043),c=s(184),u=function(e){return(0,c.jsx)("div",{style:{height:"70px"},children:(0,c.jsx)("table",{children:(0,c.jsxs)("tbody",{children:[(0,c.jsxs)("tr",{children:[(0,c.jsx)("td",{align:"center",children:(0,c.jsx)(a.Z,{imgURL:e.userAvatarImageURL})}),(0,c.jsx)("td",{width:"80%",children:e.message})]}),(0,c.jsx)("tr",{children:(0,c.jsx)("td",{align:"center",children:(0,c.jsx)("b",{children:e.username})})})]})})})},d=function(e){return(0,c.jsx)("div",{style:{height:"200px",overflowY:"auto"},children:e.messageArray.map((function(e,n){return(0,c.jsx)("div",{children:(0,c.jsx)(u,{username:e.userName,userAvatarImageURL:e.imgURL,message:e.message})},n)}))})},o=s(4700),l=s(6023),h=function(e){var n=e.sendMessage,s=(0,i.useState)(""),r=(0,t.Z)(s,2),a=r[0],u=r[1];return(0,c.jsxs)("div",{children:[(0,c.jsx)("div",{children:(0,c.jsx)(l.Z,{onChange:function(e){u(e.currentTarget.value)}})}),(0,c.jsx)("div",{children:(0,c.jsx)(o.ZP,{onClick:function(){n(a),u("")},children:"\u041e\u0442\u043f\u0440\u0430\u0432\u0438\u0442\u044c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435"})})]})},x=function(e){return(0,c.jsxs)("div",{children:[(0,c.jsx)(d,{messageArray:e.messageArray}),(0,c.jsx)("hr",{}),(0,c.jsx)(h,{sendMessage:e.sendMessage})]})},j=s(7781),f=s(7929),g=new WebSocket("ws://localhost:8080/ws"),m=(0,j.qC)(f.D,i.memo)((function(e){var n=(0,i.useState)([]),s=(0,t.Z)(n,2),a=s[0],u=s[1];(0,i.useEffect)((function(){g.addEventListener("message",(function(e){var n=JSON.parse(e.data);console.log(n),u((function(e){return[].concat((0,r.Z)(e),(0,r.Z)(n))}))}))}),[]);return(0,c.jsx)("div",{children:(0,c.jsx)(x,{messageArray:a,sendMessage:function(e){g.send(e)}})})}))}}]);
//# sourceMappingURL=826.4915f8f0.chunk.js.map