import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DokumentService } from '../../services/dokument.service';
import { Dokument } from '../../models/dokument';

@Component({
  selector: 'jhi-add-dokument',
  templateUrl: './add-dokument.component.html',
  styleUrls: ['./add-dokument.component.scss'],
})
export class AddDokumentComponent implements OnInit {
  id: number;
  dokument: Dokument;

  constructor(privateRoute: ActivatedRoute, private router: Router, private dokumentService: DokumentService) {}

  ngOnInit(): void {
    this.dokument = new Dokument();
  }
  redirectToListOfAllDokuments() {
    this.router.navigate(['/dokumenti']);
  }

  createDokument() {
    this.dokumentService.createDokument(this.dokument).subscribe(
      data => {
        console.log(data);
        this.dokument = new Dokument();
        console.log(this.dokument);
        this.redirectToListOfAllDokuments();
      },
      error => console.log(error)
    );
  }
  onSubmit() {
    this.createDokument();
  }
}
