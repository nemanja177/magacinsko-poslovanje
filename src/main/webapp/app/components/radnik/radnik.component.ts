import { Component, OnInit } from '@angular/core';
import { Radnik } from '../../models/Radnik';
import { RadnikService } from '../../services/radnik.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-radnik',
  templateUrl: './radnik.component.html',
  styleUrls: ['./radnik.component.scss'],
})
export class RadnikComponent implements OnInit {
  radnici: Radnik[];

  constructor(private radnikService: RadnikService, private router: Router) {}

  ngOnInit(): void {
    this.radnikService.getRadnik().subscribe(radnici => {
      this.radnici = radnici;
      console.log(radnici[0]);
    });
  }

  public updateRadnik(id: number) {
    this.router.navigate(['updateRadnik', id]);
  }

  public createRadnik() {
    this.router.navigate(['createRadnik']);
  }

  public blockRadnik(id: number) {
    console.log(id);
    let response = this.radnikService.blockRadnik(id);
    response.subscribe(radnici => (this.radnici = radnici));
  }
}
