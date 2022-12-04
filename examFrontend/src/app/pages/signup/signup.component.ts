import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private userService:UserService,private snack: MatSnackBar, private router:Router) { }

  public user={
    username:'',
    password:'',
    firstName:'',
    lastName:'',
    email:'',
    phone:''
    };

  ngOnInit(): void {
  }

 
  formSubmit()
  {
    
    //Validation
    if(this.user.username==''|| this.user.username==null)
    {
      this.snack.open("Username cannot be empty!","ok",{
        duration:3000,
        verticalPosition:'bottom',
      });
      return;
    }

    //Password length
    var passw= /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{8,15}$/;
    if(this.user.password.match(passw)==null)
    {
      this.snack.open("Password between 8 to 15 and contain at least one\nlowercase letter, uppercase letter, numeric digit\nand special character","ok",{
        duration:3000,
        verticalPosition:'bottom',
      });
      return;
    }

    //Email
    var email=/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(this.user.email.match(email)==null)
    {
      this.snack.open("Invalid Email","ok",{
        duration:3000,
        verticalPosition:'bottom',
      });
      return;
    }

    //Username check at backend if username already present then error is returned
    this.userService.addUser(this.user).subscribe(
      (data)=>{//success
        console.log(data);
        Swal.fire("Thank You","You Registered Successfully",'success');
        this.router.navigate(['login']);
      },
      (error)=>{//error
        this.snack.open("Username already present!","ok",{
          duration:3000,
          verticalPosition:'bottom',
        });
      }
    );
  }

}
