import { Component, OnInit } from '@angular/core';
import { Dokument } from '../../models/dokument';
import { DokumentService } from '../../services/dokument.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-dokument',
  templateUrl: './dokument.component.html',
  styleUrls: ['./dokument.component.scss'],
})
export class DokumentComponent implements OnInit {
  dokumenti: Dokument[] = [];

  constructor(private dokumentService: DokumentService, private router: Router) {}

  ngOnInit(): void {
    this.dokumentService.getDokument().subscribe(dokumenti => {
      this.dokumenti = dokumenti;
      console.log(dokumenti[0]);
    });
  }

  public updateDokument(id: number) {
    this.router.navigate(['updateDokument', id]);
  }

  public createDokument() {
    this.router.navigate(['createDokument']);
  }

  public blockDokument(id: number) {
    console.log(id);
    let response = this.dokumentService.blockDokument(id);
    response.subscribe(dokumenti => (this.dokumenti = dokumenti));
  }
}
