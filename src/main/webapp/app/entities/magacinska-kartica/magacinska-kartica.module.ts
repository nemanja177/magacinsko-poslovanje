import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MagacinskaKarticaComponent } from './list/magacinska-kartica.component';
import { MagacinskaKarticaDetailComponent } from './detail/magacinska-kartica-detail.component';
import { MagacinskaKarticaUpdateComponent } from './update/magacinska-kartica-update.component';
import { MagacinskaKarticaDeleteDialogComponent } from './delete/magacinska-kartica-delete-dialog.component';
import { MagacinskaKarticaRoutingModule } from './route/magacinska-kartica-routing.module';

@NgModule({
  imports: [SharedModule, MagacinskaKarticaRoutingModule],
  declarations: [
    MagacinskaKarticaComponent,
    MagacinskaKarticaDetailComponent,
    MagacinskaKarticaUpdateComponent,
    MagacinskaKarticaDeleteDialogComponent,
  ],
})
export class MagacinskaKarticaModule {}
