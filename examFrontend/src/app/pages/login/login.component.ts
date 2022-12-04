import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData={
    username:'',
    password:''
  }
  constructor(private snack:MatSnackBar, private login:LoginService , private router:Router) { }

  ngOnInit(): void {
  }

  formSubmit()
  {
    
    if(this.loginData.username.trim()=='' || this.loginData.username==null)
    {
        this.snack.open("Username required !!" , '',{
          duration:3000,
        });
        
    return;
    }
    
  if(this.loginData.password.trim()=='' || this.loginData.password==null)
    {
        this.snack.open("Password required !!" , '',{
          duration:3000,
        });
    return;
    }
    this.login.generateToken(this.loginData).subscribe(
    (data:any)=>
    {
      console.log('success');
      console.log(data);
      this.login.loginUser(data.token);
      this.login.getCurrentUser().subscribe(
      (user:any)=>
      {
        this.login.setUser(user);
        console.log(user);
        console.log(this.login.getUserRole());

        //redirect to admin
        if(this.login.getUserRole()=='admin'){
          this.router.navigate(['admin']) ;
          this.login.loginStatusSubject.next(true);
        }//redirect to user
        else if(this.login.getUserRole()=="normal"){
          this.router.navigate(['user-dashboard']); 
          this.login.loginStatusSubject.next(true);
        }//else logout
        else{
          this.login.logout();
        }
      }
      );
    },
    (error)=>{
      this.snack.open("Wrong Password! Try Again","ok",{
        duration:3000,
        verticalPosition:'bottom',
      });
      console.log(error);
    });
  }

}
