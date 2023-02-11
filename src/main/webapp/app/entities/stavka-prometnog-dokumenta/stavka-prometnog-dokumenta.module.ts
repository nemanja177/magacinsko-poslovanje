import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StavkaPrometnogDokumentaComponent } from './list/stavka-prometnog-dokumenta.component';
import { StavkaPrometnogDokumentaDetailComponent } from './detail/stavka-prometnog-dokumenta-detail.component';
import { StavkaPrometnogDokumentaUpdateComponent } from './update/stavka-prometnog-dokumenta-update.component';
import { StavkaPrometnogDokumentaDeleteDialogComponent } from './delete/stavka-prometnog-dokumenta-delete-dialog.component';
import { StavkaPrometnogDokumentaRoutingModule } from './route/stavka-prometnog-dokumenta-routing.module';

@NgModule({
  imports: [SharedModule, StavkaPrometnogDokumentaRoutingModule],
  declarations: [
    StavkaPrometnogDokumentaComponent,
    StavkaPrometnogDokumentaDetailComponent,
    StavkaPrometnogDokumentaUpdateComponent,
    StavkaPrometnogDokumentaDeleteDialogComponent,
  ],
})
export class StavkaPrometnogDokumentaModule {}
