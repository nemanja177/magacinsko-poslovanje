import { Component, OnInit } from '@angular/core';
import { Radnik } from '../../models/Radnik';
import { RadnikService } from '../../services/radnik.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'jhi-add-radnik',
  templateUrl: './add-radnik.component.html',
  styleUrls: ['./add-radnik.component.scss'],
})
export class AddRadnikComponent implements OnInit {
  id: number;
  radnik: Radnik;

  constructor(privateRoute: ActivatedRoute, private router: Router, private radnikService: RadnikService) {}

  ngOnInit(): void {
    this.radnik = new Radnik();
  }
  redirectToListOfAllRadnici() {
    this.router.navigate(['/radnici']);
  }

  createRadnik() {
    this.radnikService.createRadnik(this.radnik).subscribe(
      data => {
        console.log(data);
        this.radnik = new Radnik();
        console.log(this.radnik);
        this.redirectToListOfAllRadnici();
      },
      error => console.log(error)
    );
  }
  onSubmit() {
    this.createRadnik();
  }
}
