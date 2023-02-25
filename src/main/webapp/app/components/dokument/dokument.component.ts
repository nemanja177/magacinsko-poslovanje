import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-dokument',
  templateUrl: './dokument.component.html',
  styleUrls: ['./dokument.component.scss'],
})
export class DokumentComponent implements OnInit {
  route: any;
  documentID;
  document;
  documentItems;
  constructor() {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.documentID = params['id'];
      console.log(this.documentID);
    });
  }

  showDocumentItem() {
    document.getElementById('documentItem').style.display = 'block';
  }

  closeDocumentItem() {
    document.getElementById('documentItem').style.display = 'none';
  }
}
